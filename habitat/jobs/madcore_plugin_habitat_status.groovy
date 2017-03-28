pipelineJob('madcore.plugin.habitat.status') {
    parameters {
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage 'Habitat: status'
                build job: 'madcore.habitat.status'
                }
            """.stripIndent())
	    }
    }
}
