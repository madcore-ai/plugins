pipelineJob('madcore.plugin.k8s.delete') {
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('K8S: delete') {
                }
                }
            """.stripIndent())
	    }
    }
}
