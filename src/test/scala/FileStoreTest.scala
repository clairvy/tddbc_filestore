import org.specs._

import org.tddbcnagoya.filestore._
import java.util.Date

object FileStoreSpecs extends Specification {
  "dump できる" should {
    "dump して 空文字を返す" in {
      val fstore = new FileStore()
      fstore.dump must_==""
    }
    "dump して 'key1:value1\n' 返す" in {
      val fstore = new FileStore()
      fstore.set("key1", "value")
      fstore.dump must_=="key1:value\n"

    }

    "dump して 'key1:value1\nkey2:value2\n'を返す" in {
      val fstore = new FileStore()
      fstore.set("key1", "value1")
      fstore.set("key2", "value2")
      fstore.dump must_== "key1:value1\nkey2:value2\n"
    }

    "同じkeyで順番が変る key1, key2, key1でkey2,key1の順" in {
      val fstore = new FileStore()
      fstore.set("key1", "value1")
      fstore.set("key2", "value2")
      fstore.set("key1", "value3")
      fstore.dump must_== "key2:value2\nkey1:value3\n"
    }

    "キィが空文字列だったら 何もしない" in {
      val fstore = new FileStore()
      fstore.set("", "value1")
      fstore.dump must_== ""
    }

    "キィがnull だったら 何もしない" in {
      val fstore = new FileStore()
      fstore.set(null, "value1")
      fstore.dump must_== ""
    }

    "get できるよ" in {
      val fstore = new FileStore()
      fstore.set("key1", "value3")
      fstore.get("key1") must_== Some("value3")
    }

    "get できるよ" in {
      val fstore = new FileStore()
      fstore.set("key2", "value2")
      fstore.get("key2") must_== Some("value2")
    }

    "get できぬ" in {
      val fstore = new FileStore()
      fstore.get("key2") must_== None
    }
  }

  // 仕様変更1
  "multi できる" should {
    "multi のset できる" in {
      val fstore = new FileStore()
      fstore.set_multi(List(("key1", "value1"), ("key2", "value2")))
      fstore.get("key1") must_== Some("value1")
      fstore.get("key2") must_== Some("value2")
    }

    "multi の get できる" in {
      val fstore = new FileStore()
      fstore.set_multi(List(("key1", "value1"), ("key2", "value2")))
      fstore.get_multi(List("key1", "key2","key3")) must_== List(("key1", "value1"), ("key2", "value2"))
    }
  }

  // 仕様変更2
  "set で ${now} 使える" should {
    "${now} 入ってる" in {
      val fstore = new FileStore()
      fstore.set("key1", "aaa${now}bbb")
      val now = fstore.get_time("key1").get
      fstore.get("key1") must_== Some("aaa" + now + "bbb")
    }
  }
}

// 1. set キィ+バリューストア / get
//    dump => String で キィ:バリュー改行のフォーマット
// 2. 空文字列 or nill キィは変化無し
// 3. 既存のキィで更新すると，dump したときに順番が後ろに変化する

// RKTM のー．ちょっと良い仕様を見てみたい
// 仕様変更1
// 4. 2個以上のペアをセットできる．一つのメソッドで 1引数で
// 5. 2個以上のキィを渡して，キィ，バリューのペアのリストを返す
//    無いキィの場合は，スルー．(同数が返る訳ではない．)
// 仕様変更2
// 6. ${now} 部分が set したときの時刻？で，value が置き換えらえられる
// 仕様変更3
// 7. set で，消える相対時間と絶対時刻を指定できる
