(ns property-based-testing-intro.olio-draw-core
  (:require [quil.core :as q]
            [property-based-testing-intro.olio-draw :as o]))

(q/defsketch sketch-olio
  :title "Olio"
  :setup o/setup
  :draw o/draw
  :size [315 440])
