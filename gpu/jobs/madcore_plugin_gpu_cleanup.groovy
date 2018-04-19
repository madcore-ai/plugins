pipelineJob('madcore.plugin.gpu.cleanup') {
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('gpuCluster: nodes cleanup') {
                    build job: 'madcore.kubectl.cluster.nodes.cleanup', parameters: [string(name: 'NODE_LABEL', value: 'cluster=gpu')]
                }
                }
            """.stripIndent())
	    }
    }
}
