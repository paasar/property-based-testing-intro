(ns property-based-testing-intro.simple-test
  (:require [clojure.test :refer :all]
            [property-based-testing-intro.simple :refer :all]))

(deftest a-test
  (testing "I'm a test"
    (is (= 0 1))))

(deftest add-test
  (testing "1+1=2"
    (is (= 2 (add 1 1)))))

