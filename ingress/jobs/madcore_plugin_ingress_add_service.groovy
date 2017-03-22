pipelineJob('madcore.plugin.ingress.add.service') {
  parameters {
    stringParam('SERVICE_NAME', '', 'service name for ingress trafic')
    stringParam('SERVICE_PORT', '', 'service port for ingress trafic')
    stringParam('SERVICE_NAMESPACE', '', 'service namespace')
    stringParam('APP_NAME', '', 'subdomain for plugin')

  }
  definition {
    cps {
          sandbox()
          script("""
          node {
              stage('Render template') {
                sh "python /opt/madcore/bin/render_ingress.py '\${APP_NAME}' '\${SERVICE_NAME}' '\${SERVICE_PORT}' '\${SERVICE_NAMESPACE}' "
              }
              stage('Add rule to kubernetes') {
                sh "kubectl create -f /opt/ingress"
              }
            }
          """.stripIndent())
    }
  }
}
