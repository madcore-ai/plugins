pipelineJob('madcore.plugin.k8s.status') {
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('K8S: status') {
                    build job: 'madcore.kubectl.cluster.nodes.status', parameters: [string(name: 'NODE_LABEL', value: 'cluster=k8s')]
                }
                }
            """.stripIndent())
	    }
    }
}
