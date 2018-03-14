(ns property-based-testing-intro.simple-test
  (:require [clojure.test :refer :all]
            [property-based-testing-intro.simple :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

;unit test
(deftest add-unit-test
  (testing "1+1=2"
    (is (= 2 (add 1 1))))

  (testing "1+2=3"
    (is (= 3 (add 1 2))))

  (testing "0+1=1"
    (is (= 1 (add 0 1)))))

(defn gen-int [] (gen/generate gen/int))

;input generation
(deftest add-property-test
  (testing "When I add two numbers, the result should not depend on parameter order"
    (let [x (gen-int)
          y (gen-int)]
      (is (= (add x y) (add y x)))))

  (testing "Adding 1 twice is the same as adding 2"
    (let [x (gen-int)]
      (is (= (add 1 (add 1 x)) (add 2 x)))))

  (testing "Adding zero is the same as doing nothing"
    (let [x (gen-int)]
      (is (= x (add 0 x))))))

;defspec
(defspec result-should-not-depend-on-parameter-order
         100
         (prop/for-all [x gen/int
                        y gen/int]
           (= (add x y) (add y x))))

(defspec adding-1-twice-is-the-same-as-adding-2
         100
         (prop/for-all [x gen/int]
           (= (add 1 (add 1 x)) (add 2 x))))

(def prop-adding-zero-is-the-same-as-doing-nothing
  (prop/for-all [x gen/int]
    (= x (add 0 x))))

(defspec adding-zero-is-the-same-as-doing-nothing
         100
         prop-adding-zero-is-the-same-as-doing-nothing)

;shrinking, quick-check and results
(def prop-no-42
  (prop/for-all [v (gen/vector gen/int)]
    (not (some #{42} v))))

(tc/quick-check 100 prop-no-42)

