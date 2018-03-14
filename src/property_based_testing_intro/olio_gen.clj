(ns property-based-testing-intro.olio-gen
  (:require [clojure.test.check.generators :as gen]))

(def names-part-1 ["Cataclysmic" "Combustible" "Noxious" "Torrential" "Verdurous" "Merciful" "Fervent" "Glorified" "Indomitable" "Mindful" "Ancient" "Belligerent" "Thrashing" "Brazen" "Champion of" "Colossal" "Cryptic" "Dauntless" "Defiant" "Duskborne" "Electrostatic" "Ravenous" "Regal" "Solitary" "Sparring" "Stalking" "Stampeding" "Steadfast" "Sun-Blessed" "Tattered" "Territorial" "Verdant" "Vicious" "Visionary" "Vizier of the" "Walking" "Watchful" "Wily" "Wretched" "Zealot of the"])

(def names-part-2 ["Gearhulk" "Oketra" "Kefnet" "Bontu" "Hazoret" "Rhonas" "Tyrant" "Khenra" "Pummeler" "Etali" "Ghalta" "Paladin" "Forerunner" "Aetherborn" "Gishath" "Gonti" "Hapatra" "Hydra" "Apprentice" "Jace" "Liliana" "Gideon" "Chandra" "Nissa" "Kari Zev" "Kinjalli" "Tilonalli" "Cub" "Branchwalker" "Mimic" "Mummy" "Nezahal" "Otepec" "Padeem" "Ramunap" "Phoenix" "Raptor" "Khenra" "Gremlin" "Goblin" "Skymarcher" "Cutthroat" "Champion" "Oracle" "Dragon" "Naga" "Kavu" "Thopter" "Camel" "Mage" "Tetzimoc" "God" "Rats" "Tocatli" "Prophet" "Ballista" "Yahenni" "Zacama" "Zetalpa"])

(def name-gen (gen/fmap (fn [[f l]] (str f " " l))
                        (gen/tuple (gen/elements names-part-1)
                                   (gen/elements names-part-2))))

(def cost-gen (gen/fmap (fn [[colorless color color-count]]
                          (cons colorless (repeat color-count color)))
                        (gen/tuple (gen/frequency [[1 (gen/return nil)] [9 (gen/choose 1 6)]])
                                   (gen/elements [:w :u :b :r :g])
                                   (gen/choose 1 3))))

(def color-gen (gen/tuple (gen/choose 0 255)
                          (gen/choose 0 255)
                          (gen/choose 0 255)))

(def shape-gen (gen/elements [:circle :triangle :rectangle]))

(def picture-gen (gen/fmap (fn [[bg-color head-shape head-color body-shape body-color]]
                             {:background-color bg-color
                              :head-shape head-shape
                              :head-color head-color
                              :body-shape body-shape
                              :body-color body-color})
                           (gen/tuple color-gen shape-gen color-gen shape-gen color-gen)))

(def power-and-toughness-gen (gen/fmap (fn [[power toughness]] (str power "/" toughness))
                                       (gen/tuple (gen/choose 0 9)
                                                  (gen/choose 1 9))))

(def olio-gen (gen/fmap (fn [[name cost picture power-and-toughness]]
                          {:name name
                           :cost cost
                           :picture picture
                           :power-and-toughness power-and-toughness})
                        (gen/tuple name-gen cost-gen picture-gen power-and-toughness-gen)))

(defn generate-olio []
  (gen/generate olio-gen))

