pipelineJob('madcore.plugin.mailgunflanker.render.template') {
    parameters {
      stringParam('APP_NAME', 'mailgunflanker', '')
    }

    steps {
      definition {
        cps {
              sandbox()
              script("""
              node {
                stage ('rendering rc template') {
                  sh 'cat /opt/plugins/mailgunflanker/templates/rc.yaml.template | sed -e "s/{APP_NAME_TMPL}/\$APP_NAME/g" > /opt/plugins/mailgunflanker/kub/rc.yaml'
                }
              }
              """.stripIndent())
        }
      }
   }
}
