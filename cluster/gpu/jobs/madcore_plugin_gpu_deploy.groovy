pipelineJob('madcore.plugin.gpu.deploy') {
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('GPU: install') {
                }
                }
            """.stripIndent())
	    }
    }
}
