(ns property-based-testing-intro.simple)

(defn add' [x y]
  (cond
   (= y 2) 3
   (= x 0) y
   :else 2))

(def add'' #(if (pos? %2) (+ %1 %2) 0))

(def add +)

