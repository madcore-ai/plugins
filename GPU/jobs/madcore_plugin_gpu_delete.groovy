pipelineJob('madcore.plugin.GPU.delete') {
    parameters {
	    stringParam('APP_NAME', 'GPU', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Kubernetes: delete') {
                    build job: 'madcore.kubectl.delete', parameters: [string(name: 'FILENAME', value: 'GPU/kub')]
                }
                stage('SparkCluster: nodes cleanup') {
                    build job: 'madcore.kubectl.cluster.nodes.cleanup', parameters: [string(name: 'NODE_LABEL', value: 'cluster=GPU')]
                }
                }
            """.stripIndent())
	    }
    }
}
