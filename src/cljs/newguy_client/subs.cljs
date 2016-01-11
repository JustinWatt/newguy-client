(ns newguy-client.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :name
 (fn [db]
   (reaction (:name @db))))

(re-frame/register-sub
 :active-panel
 (fn [db _]
   (reaction (:active-panel @db))))

(re-frame/register-sub
 :animals
 (fn [db _]
   (reaction (vals (get-in @db [:animals])))))

(re-frame/register-sub
 :search-filter
 (fn [db _]
   (reaction (:search-filter @db))))

(defn matches-query?
  [search-filter animal]
  (if (= "" search-filter)
    true
    (boolean (or
              (re-find (re-pattern search-filter) (.toLowerCase (:name animal)))
              (re-find (re-pattern search-filter) (.toLowerCase (:breed animal)))))))

(re-frame/register-sub
 :filtered-animals
 (fn [db _]
   (let [search-filter (re-frame/subscribe [:search-filter])
         animals       (re-frame/subscribe [:animals])
         active-yard   (re-frame/subscribe [:active-yard])]
     (reaction (->> @animals
                    (filter (partial matches-query? @search-filter))
                    (filter #(not= @active-yard (:yard_id %))))))))

(re-frame/register-sub
 :animals-in-yard
 (fn [db [_ yard_id]]
   (let [animals (re-frame/subscribe [:animals])]
     (reaction (->> @animals
                    (filter #(= (:yard_id %) yard_id)))))))

(re-frame/register-sub
 :animals-in-active-yard
 (fn [db [_]]
   (let [active-yard-id (re-frame/subscribe [:active-yard])
         animals (re-frame/subscribe [:animals])]
     (reaction (->> @animals
                    (filter #(= (:yard_id %) @active-yard-id)))))))

(re-frame/register-sub
 :yards
 (fn [db _]
   (reaction (vals (get-in @db [:yards])))))

(re-frame/register-sub
 :yard-by-id
 (fn [db [_ yard_id]]
   (reaction (get-in @db [:yards yard_id]))))

(re-frame/register-sub
 :active-yard
 (fn [db]
   (reaction (:active-yard @db))))

(re-frame/register-sub
 :active-yard-details
 (fn [db [_]]
   (let [yard-id (re-frame/subscribe [:active-yard])]
     (reaction (get-in @db [:yards @yard-id])))))

(re-frame/register-sub
 :active-animal
 (fn [db [_]]
   (reaction (@db :active-animal))))

(re-frame/register-sub
 :active-animal-relationships
 (fn [db _]
   (let [active-animal (re-frame/subscribe [:active-animal])]
     (reaction (get-in @db [:relations @active-animal])))))

(re-frame/register-sub
 :test
 (fn [db _]
   (reaction (:test @db))))
