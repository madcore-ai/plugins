pipelineJob('madcore.plugin.gpu.delete') {
    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('GPU: delete') {
                }
                }
            """.stripIndent())
	    }
    }
}
