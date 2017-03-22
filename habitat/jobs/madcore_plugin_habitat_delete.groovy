pipelineJob('madcore.plugin.habitat.delete') {
    parameters {
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage 'Habitat: delete'
                build job: 'madcore.habitat.delete'
                }
            """.stripIndent())
	    }
    }
}
