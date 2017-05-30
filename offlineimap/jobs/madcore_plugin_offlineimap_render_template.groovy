pipelineJob('madcore.plugin.offlineimap.render.template') {
    parameters {
      stringParam('APP_NAME', 'offlineimap', '')
      stringParam('ACCOUNT', 'mysecret', '')


    }

    steps {
      definition {
        cps {
              sandbox()
              script("""
              node {
                stage ('rendering rc template') {
                  sh 'cat /opt/plugins/offlineimap/templates/rc.yaml.template |sed -e "s/{APP_NAME_TMPL}/\$APP_NAME/g" |sed -e "s/{SECRET_TMPL}/\$ACCOUNT/g" > /opt/plugins/offlineimap/kub/rc.yaml'
                }
              }
              """.stripIndent())
        }
      }
   }
}
