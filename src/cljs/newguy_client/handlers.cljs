(ns newguy-client.handlers
  (:require [com.rpl.specter :as s]
            [newguy-client.db :as db]
            [re-frame.core :as re-frame]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/register-handler
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/register-handler
 :set-search-filter
 (fn [db [_ search-filter]]
   (assoc-in db [:search-filter] (.trim (.toLowerCase search-filter)))))

(re-frame/register-handler
 :change-animal-yard
 (fn [db [_ animal_id yard_id]]
   (assoc-in db [:animals animal_id :yard_id] yard_id)))

(re-frame/register-handler
 :set-active-yard
 (fn [db [_ active-yard]]
   (assoc db :active-yard active-yard)))

(defn get-dogs-in-yard [db yard]
  (->> db
       :animals
       vals
       (map :yard_id)
       (filter #(= yard %))))

(re-frame/register-handler
 :empty-yard
 (fn [db [_ active-yard]]
   (let [dogs-in-yard (get-dogs-in-yard db active-yard)]
     (->> db
          (iterate #(assoc-in db [:animals % :yard_id] nil))
          (drop (count dogs-in-yard))
          first))))

(re-frame/register-handler
 :set-active-animal
 (fn [db [_ animal-id]]
   (assoc db :active-animal animal-id)))
