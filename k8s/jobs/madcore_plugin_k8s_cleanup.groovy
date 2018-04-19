pipelineJob('madcore.plugin.k8s.cleanup') {
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('K8SCluster: nodes cleanup') {
                    build job: 'madcore.kubectl.cluster.nodes.cleanup', parameters: [string(name: 'NODE_LABEL', value: 'cluster=k8s')]
                }
                }
            """.stripIndent())
	    }
    }
}
