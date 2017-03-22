pipelineJob('madcore.plugin.ingress.deploy') {
  parameters {
    stringParam('IngressInstanceIP', '', 'IP address Ingress instance from cf output')
  }
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Ingress: cloud formation install') {
                }
                stage('Rendering configmap') {
                  sh "python /opt/madcore/bin/render_ingress_configmap.py"
                }
                stage('Ingress: deploy pods') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'cluster/ingress/kub')]
                }
              }
            """.stripIndent())
	    }
    }
}
