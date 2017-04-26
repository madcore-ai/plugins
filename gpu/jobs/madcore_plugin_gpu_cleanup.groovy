pipelineJob('madcore.plugin.GPU.cleanup') {
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('GPUCluster: nodes cleanup') {
                    build job: 'madcore.kubectl.cluster.nodes.cleanup', parameters: [string(name: 'NODE_LABEL', value: 'cluster=GPU')]
                }
                }
            """.stripIndent())
	    }
    }
}
