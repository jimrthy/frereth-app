(ns com.frereth.app
  (:require [schema.core :as s]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Specs

(def world-state
  "How you represent your world is your business"
  {s/Any s/Any})

(def key-sym
  "Realistically, ought to enumerate exactly which are possible"
  s/Keyword)
(def key-direction (s/enum [:up :down :repeat]))
(def key-event
  {:which key-sym
   :direction key-direction})

;; Note that life gets much more interesting with multi-touch
(def click-event-type (s/enum [:down :up :move :hover :double-tap]))
(def click-position {:x s/Int, :y s/Int})
(def click-event
  {:what click-event-type
   :where click-position})

(def drawing-instruction
  "This is where things get weird. What does it even mean?"
  s/Keyword)

(def world-events
  "These are the handlers for events that happen to a World"
  {(s/Maybe :on-tick) {:tock (s/fn world-state world-state)
                       (s/Maybe :rate) (s/fn s/Int)}
   (s/Maybe :on-key) (s/fn world-state world-state key-event)
   (s/Maybe :on-mouse (s/fn world-state world-state mouse-event))
   (s/Maybe) :stop-when (s/fn s/Bool world-state)
   (s/Maybe :on-draw [drawing-instructions] world-state)})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public

(s/defn ^:always-validate big-bang
  "Realistically, this is actually a clojurescript function"
  ([initial-state :- world-state]
   (big-bang initial-state {}))
  ([initial-state :- world-state
    handlers :- renderer-events]
   (throw (ex-info "Not Implemented Yet" {}))))
