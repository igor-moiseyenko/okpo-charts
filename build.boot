(def project 'okpo-charts)
(def version "0.1.0-SNAPSHOT")

(set-env! :source-paths   #{"src" "test"}
          :resource-paths #{"resources"}
          :dependencies   '[[org.clojure/clojure "1.9.0"]
                            [org.clojure/data.csv "0.1.4"]
                            [adzerk/boot-test "1.2.0" :scope "test"]
                            [onetom/boot-lein-generate "0.1.3" :scope "test"]
                            [jfree/jfreechart "1.0.13"]
                            [seesaw "1.5.0"]])

(task-options!
 aot {:namespace   #{'okpo-charts.core}}
 pom {:project     project
      :version     version
      :description "FIXME: write description"
      :url         "http://example/FIXME"
      :scm         {:url "https://github.com/yourname/okpo-charts"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}}
 repl {:init-ns    'okpo-charts.core}
 jar {:main        'okpo-charts.core
      :file        (str "okpo-charts-" version "-standalone.jar")})

; https://github.com/boot-clj/boot/wiki/For-Cursive-Users
(require 'boot.lein)
(boot.lein/generate)

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask run
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (with-pass-thru fs
    (require '[okpo-charts.core :as app])
    (apply (resolve 'app/-main) args)))

(require '[adzerk.boot-test :refer [test]])
