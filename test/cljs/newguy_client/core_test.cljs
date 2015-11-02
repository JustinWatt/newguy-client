(ns newguy-client.core-test
  (:require
   [cljs.test :refer-macros [deftest testing is run-tests]]
   [newguy-client.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
