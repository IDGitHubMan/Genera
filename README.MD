# Genera

## What is this?

This is a simple project that is meant to explore/construct generative artworks by developing systems or "gens." These gens can then be customized, seeded, etc (maybe connected as a future feature) to create interesting visuals.

### As an example...

Imagine a collection of particles. Each of these particles has an independent range in which they can detect other particles. If a connections is detected, draw a line.

Really simple. Build a processing sketch that represents the system, extrapolate to create a js version, then build it for web.

## What are the plans?

All in all, I just want to make a bunch of cool gens that can produce cool visuals, but there need to be goals/plans/limits, so here are some:

### Particle Gen

- [x] Build a basic particle system
- [ ] Incorporate several different motion systems, such as:
  - [x] Brownian (Random) Motion
  - [ ] Flow fields based on a noise system
  - [ ] Flow fields based on a point system
  - [ ] Flocking systems
- Add some rendering rules to create the interesting visuals
  - [ ] Conditional coloring styles
  - [ ] Aformentioned connections
  - [ ] Whatever else I come up with

### Automata Gen

- [ ] Implement a basic pixel/grid tracking system
- [ ] Expand to include customizable cellular automata rules
- [ ] Add in customizable SIS infection model

### Smoke Gen

- [ ] Implement customizable ["Rainbow Smoke"](https://www.youtube.com/watch?v=dVQDYne8Bkc) alike

## Why this project?

This project is an exploration of emergence and chaos.

### Emergence

Emergence is defined as the phenomenon where complex patterns and behaviors are formed from a collection of simple individual parts that are not themselves aware of the greater complexity they are creating.

Something about emergence is deeply fascinating to me. Each of these systems is meant to create complex visuals that are beautiful to behold, but there is almost no direct control of that output. Emergence appears in two ways:

1.  The individuals that make up each system are not aware of the larger process they are part of (at least not fully). Each of these systems has a class, and a class within that class that defines the most basic unit. Everything else is handled by the outer class, which serves as an engine.
2.  The systems themselves aren't directly conceived as art-makers. They take in numbers, modify them, then output more numbers, which are then converted or processed into geometry. Thus, not every set of inputs will be amazing.

### Chaos

Isn't it amazing how almost every aspect of reality can be reduced to a mathematical formula, constant, or sequence? That we can define things in such exacting and linear ways? But even within those structures, there are often things we can't fully make sense of. Most of these gens are (in a technical sense, if unseeded) stochastic. Which means to say you can't quite know exactly what will happen, beacuse chance plays a role. That chance doesn't destroy what exists, but amplifies it by adding variety, texture, and color to what we get from a constant or formula.

### Finally...

This whole project is meant to serve a few purposes for me:

- 1.  Catalog the things I do in processing/p5js.
  - Processing is really cool, and you can make some really great stuff with it. I have made tons of stuff that I eventually misplaced/lost so this is a record/museum.
- 2. Allow me to learn/practice deeper concepts in both mathematics and programming.
  - A lot of the things I'm attempting to do have been done before, or have libraries devoted to them, etc. That's great, and is a really useful thing. However, I'm challenging myself to learn the basics and systems behind them because in that way, I can have more fun with testing their limits. It's a lot easier to customize a PC when you know what each part is and what it does. This is the same idea.
