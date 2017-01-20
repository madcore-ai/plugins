pipelineJob('madcore.plugin.k8s.status') {
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('K8S: status') {
                }
                }
            """.stripIndent())
	    }
    }
}
