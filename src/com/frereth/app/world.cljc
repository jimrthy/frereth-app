(ns com.frereth.app.world
  "Translation of the world/universe spec from racket's
_Functional I/O: Fun Framework for Freshmen_ paper

Note that this is deliberately very limited. This
API came from a paper describing an approach to teaching
pre-high school students (through masters' candidates)
how to build video games.

It probably won't get very far in terms of real-world
  apps, but this *was* the original intended audience."
  (:require [schema.core :as s]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Specs

(def world-state
  "How you represent your world is your business"
  {s/Any s/Any})

(def world-id
  "UUID seems like the most likely representation"
  s/Any)

(def update-message
  "World events can trigger messages to the server
(and vice versa)
Should really be limited to EDN."
  s/Any)

(def world-result
  ":state is the next state of the world
:message is an (optional) message to post to the server

For an MMORPG, it might be something like 'This was the last move
you verified for me. I've tried to take these steps. Now I want
to start running this direction.'"
  {:state world-state
   (s/maybe) :message update-message})

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

(def drawing-instructions
  "This is where things take a turn for the weird.

It's really a map of virtual destinations to instructions
for drawing that destination.

Those 'instructions' might be a virtual DOM tree for
an ordinary SPA.

They might be a sequence of OpenGL commands for rendering a
game

You might even have a basic 'template' section that defers
to other sections in this map for rendering its components.
Maybe it encompasses multiple interaction 'panes' with a
combination of HTML widgets and direct-draw Canvas elements.

I'm deliberately leaving this part pretty much totally
unspecified for now. Mostly because I couldn't possibly
come up with the proper one-size-fits-all approach for
any given App (I have my doubts about trying to specify
even this much).

But also because I'm pretty fuzzy on what makes sense for
any particular App that I want to write."
  {s/Keyword s/Any})

(def world-events
  "These are the handlers for events that happen to a World

pNote that they really don't mesh all that well with the complex
sort of App that I'm envisioning. The idea of a global keypress
handler is totally at odds with the way the DOM (and pretty much
any modern UI framework) works."
  {(s/Maybe :on-draw drawing-instructions world-result)
   (s/Maybe :on-key) (s/=> world-result world-state key-event)
   (s/Maybe :on-mouse (s/=> world-result world-state mouse-event))
   ;; Cope with messages that arrive from the server
   (s/Maybe :on-receive) (s/=> world-result world-result)
   ;; Note that :rate is a function that you supply to specify how
   ;; many frames per second you intend to request
   (s/Maybe :on-tick) {:tock (s/=> world-result world-state)
                       (s/Maybe :rate) (s/=> s/Int)}
   ;; This seems like the most likely place to specify
   ;; a rendering engine
   (s/Maybe) :stop-when (s/fn s/Bool world-state)})

(def planet
  "A world, from the universe's perspective"
  {:name world-id
   ;; The original paper tracks a pair of TCP ports
   ;; in here, though they aren't publicly available
   ;; for the Universe.
   ;; Probably want a Socket pair for reading/writing.
   ;; TODO: Sort this out.
   })

(def universe-state
  "universes need to keep track of some world details, but
shouldn't be bothered by things like connection management"
  {s/Any s/Any})

(def world-mail
  "Messages to a World from the Universe"
  {:to world-id
   :message update-message})

(def universe-response
  "How does the universe react to any incoming event?"
  {:state universe-state  ; next universal state
   :mails [world-mail]    ; messages to return in response
   ;; worlds to forget about
   :to-discard [world-id]})

(def universe-events {:on-new (s/=> universe-response universe-state planet)
                      :on-msg (s/=> universe-response universe-state planet update-message)
                      (s/Maybe :on-disconnect) (s/=> universe-response universe-state planet)})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public

(s/defn ^:always-validate big-bang!
  "Really for setting up individual clients

This combines an initial world state with that world's event handlers

I'm getting steadily more dubious about the actual value of this
particular approach

Note that most interesting Universes will want a World dedicated
to monitoring/manipulating what's going on at the server level.

Q: What does this return?"
  ([initial-state :- world-state]
   ;; Q: Does this make any sense at all?
   (big-bang initial-state {}))
  ([initial-state :- world-state
    handlers :- world-events]
   ;; Q: What should happen here?
   (throw (ex-info "Not Implemented Yet" {}))))

(s/defn ^:always-validate
  universe!
  "This is really for setting up the server"
  [state :- universe-state
   handlers :- universe-events]
  ;; Q: What should happen here?
  (throw (ex-info "Not Implemented Yet" {})))
