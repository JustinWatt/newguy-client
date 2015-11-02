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
 (let [search-filter (re-frame/subscribe [:search-filter])
       animals       (re-frame/subscribe [:animals])]
   (fn [db _]
     (reaction (->> @animals
                    (filter (partial matches-query? @search-filter)))))))

