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

(defn animal-circle [animal]
  (let [{:keys [id name breed]} animal]
    [:div.animal.col-xs-2
     [:h4 name]
     [:p breed]]))

(defn search-results []
  (let [animals (re-frame/subscribe [:animals])]
    (fn []
      [:div
       [:div.row
        [:div.col-md-12
         (for [animal @animals]
           ^{:key (:id animal)} [animal-circle animal])]]])))

(defn search-bar []
  (fn []
    [:div.col-md-12
     [:div.form-group
      [:label {:for "Search"}]
      [:input.form-control
       {:placeholder "Search"
        :on-change #(re-frame/dispatch
                     [:set-search-filter
                      (-> % .-target.value)])}]]]))

(defn home-panel []
  [:div.container-fluid
   [:div.row [search-results]]
   [:div.row [search-bar]]])

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
