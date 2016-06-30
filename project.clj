(defproject com.frereth.app "0.1.0-SNAPSHOT"
  :description "Root library for actually using frereth"
  :url "http://frereth.com/apps"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [;; don't want to depend on frereth-server
                 ;; Maybe especially since that should really depend on this
                 [com.jimrthy.substratum "0.0.1-SNAPSHOT"]
                 [com.frereth/common "0.0.1-SNAPSHOT"]])
