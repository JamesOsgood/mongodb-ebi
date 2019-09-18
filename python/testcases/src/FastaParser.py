from datetime import datetime
from Bio import SeqIO
import hashlib

class FastaParser(object):
	'''
	Parser for FASTA https://en.wikipedia.org/wiki/FASTA_format
	'''
	def __init__(self, parent):
		self.parent = parent

	def parseFile(self, input_stream):
		fasta_sequences = SeqIO.parse(input_stream,'fasta')
		docs = []
		for fasta in fasta_sequences:
			# self.parent.log.info(fasta)
			name, sequence = fasta.id, str(fasta.seq)
			id = fasta.id
			seq = str(fasta.seq)
			hash = hashlib.md5(seq.encode()).hexdigest()
			doc = { 'sequence_id' : id,
					'meta' : self.parseId(id),
			        'description' : fasta.description,
					'seq' : { 'seq' : seq, 'hash' : hash} }
			docs.append(doc)

		return docs

	def parseId(self, id):
		# id is like ERZ1022724
		parts = {}
		parts['top'] = id[:3]
		parts['first'] = id[3:6]
		parts['second'] = id[6:]
		return parts['top'] + parts['first']

