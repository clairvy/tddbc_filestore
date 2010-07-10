
package org.tddbcnagoya.filestore

class FileStore {
  var store:List[(String, String)] = List();
  def dump = {
    store.foldLeft("")((i,p) => i + p._1 + ":" + p._2 + "\n")
  }
  def set(k : String, v : String) {
    if (k == "" || k == null)
      return 

    store = store.dropWhile( p => p._1 == k)
    store = store ++ List((k, v))
  }

  def get(k:String) = {
    store.find(p => p._1 == k) map(_._2)
  }
}
