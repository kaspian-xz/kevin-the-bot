variable "openai_api_key" {
  description = "OpenAI API Key"
}

variable "openai_api_timeout" {
  description = "OpenAI API Timeout"
}

variable "openai_api_model" {
  description = "OpenAI ChatGPT model"
}

variable "discord_api_key" {
  description = "Discord API Key"
}

variable "aws_key_name" {
  description = "AWS ssh keypair name"
}

variable "ssh_private_key_path" {
  description = "Local path to private SSH key for AWS instance"
}
