pipelineJob('madcore.plugin.gpu.status') {
    parameters {
	    stringParam('APP_NAME', 'gpu', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('gpuCluster kubernetes: status') {
                    build job: 'madcore.kubectl.describe', parameters: [string(name: 'FILENAME', value: 'gpu/kub')]
                }
                stage('gpuCluster nodes: status') {
                    build job: 'madcore.kubectl.cluster.nodes.status', parameters: [string(name: 'NODE_LABEL', value: 'cluster=gpu')]
                }
                }
            """.stripIndent())
	    }
    }
}
