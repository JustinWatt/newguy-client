(ns newguy-client.handlers
    (:require [re-frame.core :as re-frame]
              [newguy-client.db :as db]))

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
