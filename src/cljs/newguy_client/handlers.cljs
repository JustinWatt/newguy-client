(ns newguy-client.handlers
  (:require [com.rpl.specter :as s]
            [newguy-client.db :as db]
            [re-frame.core :as re-frame]
            [ajax.core :refer [GET POST]]))

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
 :set-active-animal
 (fn [db [_ animal-id]]
   (assoc db :active-animal animal-id)))

(defn key-by-keyword [items keyword-key]
  (reduce
   (fn [m item]
     (assoc m (get item keyword-key) item)) {} items))

(re-frame/register-handler
 :process-animal-response
 (fn [db [_ response]]
   (assoc db :animals (-> (js->clj response)
                       clojure.walk/keywordize-keys
                       (key-by-keyword :id)))))

(re-frame/register-handler
 :request-animals
 (fn [db _]
   (ajax.core/GET
       "http://localhost:3000/animals?"
       {:handler #(re-frame/dispatch [:process-animal-response %1])
        :error-handler #(re-frame/dispatch [:bad-animal-response %1])})
   db))


