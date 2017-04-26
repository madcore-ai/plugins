pipelineJob('madcore.plugin.GPU.status') {
    parameters {
	    stringParam('APP_NAME', 'GPU', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('GPUCluster kubernetes: status') {
                    build job: 'madcore.kubectl.describe', parameters: [string(name: 'FILENAME', value: 'GPU/kub')]
                }
                stage('GPUCluster nodes: status') {
                    build job: 'madcore.kubectl.cluster.nodes.status', parameters: [string(name: 'NODE_LABEL', value: 'cluster=GPU')]
                }
                }
            """.stripIndent())
	    }
    }
}
