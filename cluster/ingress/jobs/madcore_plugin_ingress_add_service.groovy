pipelineJob('madcore.plugin.ingress.add.service') {
  parameters {
    stringParam('SERVICE_NAME', '', 'service name for ingress trafic')
    stringParam('SERVICE_PORT', '', 'service port for ingress trafic')
    stringParam('HOSTNAME', '', 'service port for ingress trafic')
  }
  definition {
    cps {
          sandbox()
          script("""
          node {
              HOSTNAME = params.HOSTNAME
              SERVICE_NAME = params.SERVICE_NAME
              SERVICE_PORT = params.SERVICE_PORT
              stage('Render template') {
                sh "python /opt/madcore/bin/render_ingress.py '${HOSTNAME}' '${SERVICE_NAME}' '${SERVICE_PORT}' "
              }
              stage('Add rule to kubernetes') {
                sh "kubectl create -f /opt/ingress"
              }
            }
          """.stripIndent())
    }
  }
}
