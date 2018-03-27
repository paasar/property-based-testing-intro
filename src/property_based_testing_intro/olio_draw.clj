(ns property-based-testing-intro.olio-draw
  (:require [quil.core :as q]
            [property-based-testing-intro.olio-gen :refer [generate-olio]]))

(defn setup []
  (q/smooth)
  (q/frame-rate 0.4)
  (q/background 200))

(defn- cost->bg-color [cost]
  (case (last cost)
    :w [200 200 200]
    :u [140 140 255]
    :b [120 120 120]
    :r [200 100 100]
    :g [140 255 140]))

(defn- cost->color [cost]
  (case (last cost)
    :w [255 255 255]
    :u [0 0 220]
    :b [0 0 0]
    :r [220 0 0]
    :g [0 220 0]))

(defn- draw-cost [w cost]
  (let [start-x (- w (* 13 (count cost)))
        cost-and-x (map vector cost
                               (take (count cost)
                                     (iterate #(+ 17 %)
                                              start-x)))]
    (q/no-stroke)
    (doseq [[value x] cost-and-x]
      (if (number? value)
        (do
          (q/fill 170)
          (q/ellipse x 20 15 15)
          (q/fill 0)
          (q/text-size 14)
          (q/text (str value) (- x 4) 25))
        (do
          (apply q/fill (cost->color cost))
          (q/ellipse x 20 15 15))))))

(defn- draw-shape [x y shape color]
  (apply q/fill color)
  (case shape
    :circle (q/ellipse x y 100 100)
    :triangle (q/triangle x (- y 50) (+ x 50) (+ y 20) (- x 50) (+ y 20))
    :rectangle (q/rect (- x 50) (- y 50) 100 100)))

(defn- draw-eyes [x y]
  (q/stroke-weight 6)
  (q/with-stroke [255]
    (q/fill 0)
    (q/ellipse (- x 20) y 12 12)
    (q/ellipse (+ x 20) y 12 12))
  (q/no-stroke))

(defn- draw-picture [h w {:keys [background-color
                                 head-shape
                                 head-color
                                 body-shape
                                 body-color]}]
  (apply q/fill background-color)
  (q/rect 10 30 w 200)

  (let [x (/ w 1.8)
        head-y (/ h 3.5)
        body-y (/ h 2)]
    ;Head
    (draw-shape x head-y head-shape head-color)
    (draw-eyes x head-y)
    ;Body
    (draw-shape x body-y body-shape body-color)))

(defn draw []
  (let [{:keys [name cost picture power-and-toughness]} (generate-olio)
        w (- (q/width) 20)
        h (- (q/height) 20)
        bg-color (cost->bg-color cost)]
    ;Black border
    (q/fill 0)
    (q/rect 0 0 (q/width) (q/height) 8)

    ;Background
    (apply q/fill bg-color)
    (q/rect 10 10 w h 5)

    ;Name
    (q/fill 0)
    (q/text-size 14)
    (q/text name 15 26)

    ;Cost
    (draw-cost w cost)

    ;Picture
    (draw-picture h w picture)

    ;Type
    (q/stroke 0)
    (q/stroke-weight 1)
    (q/fill 170)
    (q/rect 10 230 w 20)
    (q/fill 0)
    (q/text "Creature" 15 245)

    ;Oracle
    (q/no-stroke)
    (apply q/fill bg-color)
    (q/rect 11 251 (dec w) 100)

    ;Power/Toughness
    (q/fill 170)
    (q/stroke 0)
    (q/rect (- w 45) (- h 15) 55 25)
    (q/fill 0)
    (q/text power-and-toughness (- w 35) (+ h 3))))
