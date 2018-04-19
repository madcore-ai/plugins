pipelineJob('madcore.plugin.gpu.delete') {
    parameters {
	    stringParam('APP_NAME', 'gpu', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Kubernetes: delete') {
                    build job: 'madcore.kubectl.delete', parameters: [string(name: 'FILENAME', value: 'gpu/kub')]
                }
                stage('SparkCluster: nodes cleanup') {
                    build job: 'madcore.kubectl.cluster.nodes.cleanup', parameters: [string(name: 'NODE_LABEL', value: 'cluster=gpu')]
                }
                }
            """.stripIndent())
	    }
    }
}
