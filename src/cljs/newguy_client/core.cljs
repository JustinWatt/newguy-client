(ns newguy-client.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [newguy-client.handlers]
              [newguy-client.subs]
              [newguy-client.routes :as routes]
              [newguy-client.views :as views]))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init [] 
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
