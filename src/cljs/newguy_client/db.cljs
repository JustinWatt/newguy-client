(ns newguy-client.db
  (:require [com.rpl.specter :as s]))

(def default-db
  {:name "re-frame"

   :animals {1 {:id 1 :name "Fran"    :breed "Corgi"      :yard_id 2}
             2 {:id 2 :name "New Guy" :breed "Chihuahua"  :yard_id nil}
             3 {:id 3 :name "Elley"   :breed "Terrier"    :yard_id nil}
             4 {:id 4 :name "B.T."    :breed "Chihuahua"  :yard_id nil}
             5 {:id 5 :name "Shaw"    :breed "Chihuahua"  :yard_id 1}
             6 {:id 6 :name "Rosie"   :breed "Pitbull"    :yard_id 1}}

   :relations {1 {2 100, 3 150}
               2 {6 100, 4 250}
               3 {2 100, 1 150}
               4 {2 250}
               6 {2 100}}

   :yards   {1 {:id 1 :name "Big Yard"}
             2 {:id 2 :name "Small Yard"}}

   :active-animal nil

   :active-yard 1

   :search-filter ""})
