pipelineJob('madcore.plugin.gpu.status') {
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('GPU: status') {
                }
                }
            """.stripIndent())
	    }
    }
}
