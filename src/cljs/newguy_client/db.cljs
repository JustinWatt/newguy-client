(ns newguy-client.db)

(def default-db
  {:name "re-frame"

   :animals {1 {:id 1 :name "Fran"       :breed "Corgi"      :yard_id 2}
             2 {:id 2 :name "New Guy"    :breed "Chihuahua"  :yard_id nil}
             3 {:id 3 :name "Braw Dawgg" :breed "Coarsegold" :yard_id nil}
             4 {:id 4 :name "B.T."       :breed "Chihuahua"  :yard_id nil}
             5 {:id 5 :name "Shaw"       :breed "Chihuahua"  :yard_id 1}}

   :yards   {1 {:id 1 :name "Big Yard"}
             2 {:id 2 :name "Small Yard"}}

   :search-filter ""})
