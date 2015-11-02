(ns newguy-client.views
    (:require [re-frame.core :as re-frame]
              [re-com.core :as re-com]))

;; --------------------
(defn home-title []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [re-com/title
       :label (str "Hello from " @name ". This is the Home Page.")
       :level :level1])))

(defn link-to-about-page []
  [re-com/hyperlink-href
   :label "go to About Page"
   :href "#/about"])

(defn allow-drop [e]
  (.preventDefault e))

(defn animal-circle [animal]
  (let [{:keys [id name breed]} animal]
    [:div.animal.col-md-4.col-sm-2.col-xs-4 {:id id
                           :draggable true
                           :on-drag-over allow-drop
                           :on-drag-enter allow-drop}
     [:h4 name]
     [:p breed]]))




(defn search-results []
  (let [filtered-animals (re-frame/subscribe [:filtered-animals])]
    (fn []
      [:div.text-center
       (for [animal @filtered-animals]
           ^{:key (:id animal)} [animal-circle animal])])))

(defn search-bar []
  (fn []
    [:div
     [:div.form-group
      [:label {:for "Search"}]
      [:input.form-control
       {:placeholder "Search"
        :on-change #(re-frame/dispatch
                     [:set-search-filter
                      (-> % .-target.value)])}]]]))

(defn home-panel []
  [:div.container-fluid
   [:div.row
    [:div.col-md-12.vertical-center [search-results]]]
   [:div.row
    [:div.col-md-12 [search-bar]]]])

;; --------------------
(defn about-title []
  [re-com/title
   :label "This is the About Page."
   :level :level1])

(defn link-to-home-page []
  [re-com/hyperlink-href
   :label "go to Home Page"
   :href "#/"])

(defn about-panel []
  [re-com/v-box
   :gap "1em"
   :children [[about-title] [link-to-home-page]]])

;; --------------------
(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :about-panel [] [about-panel])
(defmethod panels :default [] [:div])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
       [:div.container (panels @active-panel)])))
