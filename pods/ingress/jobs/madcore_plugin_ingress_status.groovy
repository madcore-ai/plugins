pipelineJob('madcore.plugin.flasker-hub.status') {
    parameters {
	    stringParam('APP_NAME', 'flasker-hub', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('Kubernetes: status') {
                  build job: 'madcore.kubectl.describe', parameters: [string(name: 'FILENAME', value: 'ingress/kub')]
                }
                stage('Ingress: status') {
                    build job: 'madcore.kubectl.cluster.nodes.status', parameters: [string(name: 'NODE_LABEL', value: 'Ingress')]
                }
              }
            """.stripIndent())
	    }
    }
}
