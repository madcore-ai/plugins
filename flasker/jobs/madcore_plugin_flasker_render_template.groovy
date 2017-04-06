pipelineJob('madcore.plugin.flasker.render_template') {
    parameters {
      stringParam('APP_NAME', 'flasker', '')
    }

    stages {
      stage ('rendering rc template') {
        sh 'cat /opt/madcore/plugins/flasker/templates/rc.yaml.template | sed -e "s/\${APP_NAME}/\$APP_NAME/g" > /opt/madcore/plugins/flasker/kub/rc.yaml'
      }
      stage ('rendering svc template') {
        sh 'cat /opt/madcore/plugins/flasker/templates/svc.yaml.template | sed -e "s/\${APP_NAME}/\$APP_NAME/g" > /opt/madcore/plugins/flasker/kub/svc.yaml'
      }
   }
}
