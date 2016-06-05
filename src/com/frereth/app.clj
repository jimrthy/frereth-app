(ns com.frereth.app
  (:require [schema.core :as s]))

(s/defn big-bang
  "Realistically, this is actually a clojurescript function"
  [initial-world-state])

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
