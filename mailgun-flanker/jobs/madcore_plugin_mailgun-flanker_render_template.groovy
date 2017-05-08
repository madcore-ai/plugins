pipelineJob('madcore.plugin.mailgun-flanker.render.template') {
    parameters {
      stringParam('APP_NAME', 'mailgun-flanker', '')
    }

    steps {
      definition {
        cps {
              sandbox()
              script("""
              node {
                stage ('rendering rc template') {
                  sh 'cat /opt/plugins/mailgun-flanker/templates/rc.yaml.template | sed -e "s/{APP_NAME_TMPL}/\$APP_NAME/g" > /opt/plugins/mailgun-flanker/kub/rc.yaml'
                }
              }
              """.stripIndent())
        }
      }
   }
}
