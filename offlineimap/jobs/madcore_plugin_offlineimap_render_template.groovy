pipelineJob('madcore.plugin.offlineimap.render.template') {
    parameters {
      stringParam('APP_NAME', 'offlineimap', '')
        stringParam('REMOTE_USER', 'coxodox@gmail.com', '')
        stringParam('OAUTH2_CLIENT_ID', '846427981560-l7a2oeb1m29moj183l9g0ruq0vlbbuic.apps.googleusercontent.com', '')
        stringParam('OAUTH2_CLIENT_SECRET', 'YjYdktcPwa-RBomNZvkuuTMx', '')
        stringParam('OAUTH2_REFRESH_TOKEN', '1/yR5OP7m9DnSY6oN7wvVhOYXQNSp9k9cYkjupdcyWLsjkWA7wEA-rykT_11sJkyyX', '')


    }

    steps {
      definition {
        cps {
              sandbox()
              script("""
              node {
                stage ('rendering rc template') {
                  sh 'cat /opt/plugins/offlineimap/templates/rc.yaml.template |sed -e "s/{APP_NAME_TMPL}/\$APP_NAME/g" > /opt/plugins/offlineimap/kub/rc.yaml'
                }
              }
              """.stripIndent())
        }
      }
   }
}
