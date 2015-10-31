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

