
package org.tddbcnagoya.filestore

class FileStore {
  var valueStore:List[(String, String)] = List();
  def dump = {
    valueStore.foldLeft("")((i,p) => i + p._1 + ":" + p._2 + "\n")
  }
  def set(k : String, v : String) {
    if (k == "" || k == null)
      return 

    valueStore = valueStore.dropWhile( p => p._1 == k)
    valueStore = valueStore ++ List((k, v))
  }

  def get(k:String) = {
    valueStore.find(p => p._1 == k) map(_._2)
  }

  def set_multi(l : List[(String, String)]) {
    l.map(p => this.set(p._1, p._2))
  }

  def get_multi(l : List[String]) = {
    valueStore.filter(p => l.exists(k => p._1 == k))
  }
}
