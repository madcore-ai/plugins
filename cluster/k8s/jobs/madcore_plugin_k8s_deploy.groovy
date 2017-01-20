pipelineJob('madcore.plugin.k8s.deploy') {
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('K8S: install') {
                }
                }
            """.stripIndent())
	    }
    }
}
