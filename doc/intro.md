# Introduction to com.frereth.app

Hopefully most Apps will be able to use this as their foundation for actually building
something useful on the Frereth platform

# Basic Architecture

TODO: Compare w/ android and iOS App implementation

At its highest level, an App is defined by these components:

## Required privileges

(TODO: what boundaries do phone apps use?)

The ones I know I need:

* Ability to fork specific process (esp. the shell)
* Ability to fork arbitrarily (this is what the shell does)

## Data Structure

This gets interesting when we start talking about Apps that need
to share data

Note that it's really a fairly drastic departure from the idea that
we just have its on a file system that anyone can read/write.

Q: Is that a good or bad thing?

## Rendering Engine/Version

How does the system interpret interpret whatever's returned
from on-render?

## :render-code

Pieces that are run inside a sandbox on the renderer.

Currently I'm leaning toward defining these as a vector
of (defn...) top-level forms.

A map of function names to bodies seems like it might
be a better choice.

### on-render

This returns a sequence of instructions that will be passed along
to the rendering engine to actually draw each updated frame.

## :render-events

Right now I have two different kinds of events.

Each can optionally return a message that will be forwarded
back to the server/Universe.

### DOM events

These are attached to a specific DOM element event source.

The proxy code will capture these events at the top level and
forward them along to the :handler function that's specified
in the map that describes these events.

Each event-describing map can specify dependencies. These are
are the values associated with the DOM elements the App
created. For security reasons, Apps cannot access DOM elements
created by other Apps.

### External Events

If an element is not specified, the handler is associated
with events that arrive from the Universe/server (Q: what about
P2P apps?)

These actually need to be dispatched through the Web Worker's
onmessage function.

## Universe Events

These functions handle messages from the browser.
