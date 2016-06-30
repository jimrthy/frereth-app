(ns com.frereth.app.system
  "This really needs to require/use component-dsl
Because the individual apps need to and shouldn't.
They should supply the structure/dependency info (though
calculating dependencies automatically would be better),
and then we should cope w/ the component-level stuff.

That's pretty much the entire point here."
  (:require [clojure.spec :as s]
            [com.jimrthy.component-dsl :as cpt-dsl]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Specs

(s/def ::admin (s/keys :req []
                       :opt [::db-schema]))

(s/def ::universe (s/keys :req []
                          :opt []))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public
