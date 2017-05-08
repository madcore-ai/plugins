pipelineJob('madcore.plugin.mailinput.render.template') {
    parameters {
      stringParam('APP_NAME', 'mailinput', '')
    }

    steps {
      definition {
        cps {
              sandbox()
              script("""
              node {
                stage ('rendering rc template') {
                  sh 'cat /opt/plugins/mailinput/templates/rc.yaml.template | sed -e "s/{APP_NAME_TMPL}/\$APP_NAME/g" > /opt/plugins/mailinput/kub/rc.yaml'
                }
              }
              """.stripIndent())
        }
      }
   }
}
